-- -----------------存储过程-------------------------------------------------------------
CREATE  PROCEDURE `tt`(tableSource varchar(50),mdate varchar (8),start_day varchar(10),end_day varchar(10),IN step int, in colume_name varchar(50),in colume_type  varchar(20))
BEGIN
 declare dm varchar(100);
      declare tempfile varchar(100);
      declare min_id int(11);
      declare max_id int(11);
      declare tm timestamp;
      declare maxIdSql varchar(200); 
      set @colume_name=colume_name;
      set @colume_type=colume_type;
      set @st_day=concat(substring(start_day,1,4),'-',substring(start_day,5,2),'-',substring(start_day,7,2)," 00:00:00");
      select @st_day;
      set @strat_day_timestamp=UNIX_TIMESTAMP(@st_day)*1000;
      set @e_day=concat(substring(end_day,1,4),'-',substring(end_day,5,2),'-',substring(end_day,7,2)," 23:59:59");
      select @e_day;
      set @end_day_timestamp=UNIX_TIMESTAMP(@e_day)*1000;
      set @dm = concat(tableSource,'_',mdate);
      set @tableSource=tableSource;
      if colume_type="date" then 
      set @maxIdSql=concat("select max(id) ,min(id) into @max_id, @min_id from ",@tableSource, " where ",@colume_name," >= str_to_date( '",@st_day,"' ,'%Y-%m-%d %H:%i:%s') and ",@colume_name," <=str_to_date( '",@e_day,"' ,'%Y-%m-%d %H:%i:%s')");
      else
      set @maxIdSql=concat("select max(id) ,min(id) into @max_id, @min_id from ",@tableSource, " where ",@colume_name," >= ",@strat_day_timestamp," and ",@colume_name," <= ",@end_day_timestamp);
      end if;
      select @maxIdsql;
      PREPARE max_stmt from @maxIdSql;
      EXECUTE max_stmt;
      select @max_id;
      if @max_id is not null then
      set @csql = concat("create table if not exists ",@dm , " like " ,tableSource);
      PREPARE create_stmt from @csql;
      EXECUTE create_stmt;
      select concat("transfer begin",now());
set @sc=@min_id;
if(@sc+step<@max_id) then
         set @ec=@sc+step;
      else 
         set @ec=@max_id;
      end if;
      set @flag=0;
 WHILE @sc<=@max_id && @flag<=1 DO
      start transaction;
      insert into log_transfer (step,begin_id,end_id,table_name,create_time) values(step,@sc,@ec,@tableSource,now());
      set @insql = concat("insert into ",@dm, " select * from ", @tableSource," where id BETWEEN ? and ?");
      PREPARE in_stmt from @insql;
      EXECUTE in_stmt USING @sc,@ec;
      DEALLOCATE PREPARE in_stmt;

      set @desql = concat("delete from " , @tableSource," where id BETWEEN ? and ?");
      PREPARE de_stmt from @desql;
      EXECUTE de_stmt USING @sc,@ec;
      DEALLOCATE PREPARE de_stmt;
      select concat(@sc,'-',@sc+step,'-',now());
    commit;
      set @sc=@ec;
      if @ec+step<@max_id then
         set @ec=@ec+step;
      else  
         set @ec=@max_id;
         set @flag=@flag+1;
      end if;
 END WHILE;
 end if;
END;


CREATE PROCEDURE `tt_week_job`()
begin
      declare _LastWeekStartTime date; 
        declare _LastWeekEndTime date; 
        
        set @A = date_add(curdate(), interval -1 day);  
        
        set @B = subdate( @A,date_format(@A,'%w')-1);  
        
        set @C = date_add(@B, interval -2 day);  
        set _LastWeekStartTime = ( subdate(  @C,date_format( @C,'%w')-1));  
        set _LastWeekEndTime = ( date_add(subdate(  @C,date_format( @C,'%w')-1), interval 6 day));  
       
         set @start_day=replace(_LastWeekStartTime,'-','');
         set @end_day=replace(_LastWeekEndTime,'-','');
         set @mdate=substring(@start_day,1,6);
         set @mdate2=substring(@end_day,1,6);
         set @step=1000;
       if substring(_LastWeekStartTime,1,7)=substring(_LastWeekEndTime,1,7) then
  
          call tt('ejl_battery_record',@mdate,@start_day,@end_day,@step,'time','timestamp');
          call tt('device_signal_record',@mdate,@start_day,@end_day,@step,'time','timestamp');
          
          call tt('ef_oper_log',@mdate,@start_day,@end_day,@step,'time','date');
          call tt('ef_weather',@mdate,@start_day,@end_day,@step,'solar_date','date');
          call tt('ef_user_weather',@mdate,@start_day,@end_day,@step,'create_time','date');
          call tt('ejl_health_sitting',@mdate,@start_day,@end_day,@step,'start_time','date');
          call tt('ejl_message',@mdate,@start_day,@end_day,@step,'create_time','date');
          call tt('ejl_message_mark',@mdate,@start_day,@end_day,@step,'send_time','date');
          call tt('ejl_verify_code',@mdate,@start_day,@end_day,@step,'create_time','date');
          call tt('ejl_user_login_record',@mdate,@start_day,@end_day,@step,'create_time','date');
       else
       set @startTimeLastMonthDay=last_day(str_to_date(_LastWeekStartTime,'%Y-%m-%d'));
       set @endTimeStartMonyhDay=concat(substring(_LastWeekEndTime,1,7),'-01');
       select concat(@start_day,'||',replace(@startTimeLastMonthDay,'-',''));
       select concat(@end_day,'||',replace(@endTimeStartMonyhDay,'-',''));
          
          call tt('ejl_battery_record',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'time','timestamp');
          call tt('ejl_battery_record',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'time','timestamp');
          call tt('device_signal_record',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'time','timestamp');
          call tt('device_signal_record',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'time','timestamp');
          
          call tt('ef_oper_log',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'time','date');
          call tt('ef_oper_log',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'time','date');
          
          call tt('ef_weather',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'solar_date','date');
          call tt('ef_weather',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'solar_date','date');
          
          call tt('ef_user_weather',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'create_time','date');
          call tt('ef_user_weather',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'create_time','date');
          
          call tt('ejl_health_sitting',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'start_time','date');
          call tt('ejl_health_sitting',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'start_time','date');
          
          call tt('ejl_message',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'create_time','date');
          call tt('ejl_message',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'create_time','date');
          
          call tt('ejl_message_mark',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'send_time','date');
          call tt('ejl_message_mark',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'send_time','date');
          
          call tt('ejl_verify_code',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'create_time','date');
          call tt('ejl_verify_code',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'create_time','date');
          
          call tt('ejl_user_login_record',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'create_time','date');
          call tt('ejl_user_login_record',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'create_time','date');
        end if;
end;
CREATE PROCEDURE `tt_week_job_location_origin`()
begin
      declare _LastWeekStartTime date; 
        declare _LastWeekEndTime date; 
        
        set @A = date_add(curdate(), interval -1 day);  
        
        set @B = subdate( @A,date_format(@A,'%w')-1);  
        
        set @C = date_add(@B, interval -2 day);  
        set _LastWeekStartTime = ( subdate(  @C,date_format( @C,'%w')-1));  
        set _LastWeekEndTime = ( date_add(subdate(  @C,date_format( @C,'%w')-1), interval 6 day));  
       
         set @start_day=replace(_LastWeekStartTime,'-','');
         set @end_day=replace(_LastWeekEndTime,'-','');
         set @mdate=substring(@start_day,1,6);
         set @mdate2=substring(@end_day,1,6);
         set @step=1000;
       if substring(_LastWeekStartTime,1,7)=substring(_LastWeekEndTime,1,7) then
  
          call tt('ef_location_origin',@mdate,@start_day,@end_day,@step,'time','date');
       else
       set @startTimeLastMonthDay=last_day(str_to_date(_LastWeekStartTime,'%Y-%m-%d'));
       set @endTimeStartMonyhDay=concat(substring(_LastWeekEndTime,1,7),'-01');
       select concat(@start_day,'||',replace(@startTimeLastMonthDay,'-',''));
       select concat(@end_day,'||',replace(@endTimeStartMonyhDay,'-',''));
          call tt('ef_location_origin',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'time','date');
          call tt('ef_location_origin',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'time','date');
        end if;
end;
CREATE PROCEDURE `tt_week_job_location_semi`()
begin
      declare _LastWeekStartTime date; 
        declare _LastWeekEndTime date; 
        
        set @A = date_add(curdate(), interval -1 day);  
        
        set @B = subdate( @A,date_format(@A,'%w')-1);  
        
        set @C = date_add(@B, interval -2 day);  
        set _LastWeekStartTime = ( subdate(  @C,date_format( @C,'%w')-1));  
        set _LastWeekEndTime = ( date_add(subdate(  @C,date_format( @C,'%w')-1), interval 6 day));  
       
         set @start_day=replace(_LastWeekStartTime,'-','');
         set @end_day=replace(_LastWeekEndTime,'-','');
         set @mdate=substring(@start_day,1,6);
         set @mdate2=substring(@end_day,1,6);
         set @step=1000;
       if substring(_LastWeekStartTime,1,7)=substring(_LastWeekEndTime,1,7) then
  
          call tt('ef_location_semi',@mdate,@start_day,@end_day,@step,'time','date');
       else
       set @startTimeLastMonthDay=last_day(str_to_date(_LastWeekStartTime,'%Y-%m-%d'));
       set @endTimeStartMonyhDay=concat(substring(_LastWeekEndTime,1,7),'-01');
       select concat(@start_day,'||',replace(@startTimeLastMonthDay,'-',''));
       select concat(@end_day,'||',replace(@endTimeStartMonyhDay,'-',''));
          
          call tt('ef_location_semi',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'time','date');
          call tt('ef_location_semi',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'time','date');
        end if;
end;
CREATE PROCEDURE `tt_week_job_location`()
begin
      declare _LastWeekStartTime date; 
        declare _LastWeekEndTime date; 
        
        set @A = date_add(curdate(), interval -1 day);  
        
        set @B = subdate( @A,date_format(@A,'%w')-1);  
        
        set @C = date_add(@B, interval -2 day);  
        set _LastWeekStartTime = ( subdate(  @C,date_format( @C,'%w')-1));  
        set _LastWeekEndTime = ( date_add(subdate(  @C,date_format( @C,'%w')-1), interval 6 day));  
       
         set @start_day=replace(_LastWeekStartTime,'-','');
         set @end_day=replace(_LastWeekEndTime,'-','');
         set @mdate=substring(@start_day,1,6);
         set @mdate2=substring(@end_day,1,6);
         set @step=1000;
       if substring(_LastWeekStartTime,1,7)=substring(_LastWeekEndTime,1,7) then
  		  call tt('device_mobile',@mdate,@start_day,@end_day,@step,'time','timestamp');
          call tt('ef_location_gps',@mdate,@start_day,@end_day,@step,'time','date');
          call tt('ef_location_wifi',@mdate,@start_day,@end_day,@step,'time','timestamp');
          call tt('ef_location_assist',@mdate,@start_day,@end_day,@step,'create_time','date');
       else
       set @startTimeLastMonthDay=last_day(str_to_date(_LastWeekStartTime,'%Y-%m-%d'));
       set @endTimeStartMonyhDay=concat(substring(_LastWeekEndTime,1,7),'-01');
       select concat(@start_day,'||',replace(@startTimeLastMonthDay,'-',''));
       select concat(@end_day,'||',replace(@endTimeStartMonyhDay,'-',''));
          call tt('device_mobile',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'time','timestamp');
          call tt('device_mobile',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'time','timestamp');
          
          call tt('ef_location_gps',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'time','date');
          call tt('ef_location_gps',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'time','date');
          
          call tt('ef_location_wifi',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'time','timestamp');
          call tt('ef_location_wifi',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'time','timestamp');
          call tt('ef_location_assist',@mdate,@start_day,replace(@startTimeLastMonthDay,'-',''),@step,'create_time','date');
          call tt('ef_location_assist',@mdate2,replace(@endTimeStartMonyhDay,'-',''),@end_day,@step,'create_time','date');
        end if;
end;