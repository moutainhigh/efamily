/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   Delimiters.java

package com.winterframework.efamily.server.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public final class Delimiters
{
	public static void main(String[] args){
		byte[] byteArray2="##\0**".getBytes();
		System.out.println(byteArray2.length);
		 for(byte b:byteArray2){
			 System.out.print(new String(new byte[]{b})+" ");
				System.out.print(b + " ");
			}
		 System.out.println();
		 byte[] byteArray=getDelimiter();
		 for(byte b:byteArray){
			 System.out.print(new String(new byte[]{b})+" ");
				System.out.print(b + " ");
			}
	}
	public static byte[] getDelimiter(){ 
		final String delimiter="##_**";		
		byte[] byteArray=delimiter.getBytes();
		/*int i=0;
		for(byte b:byteArray){
			byteArray[i]=b>>>=2;
			i++;
		}*/
		return byteArray;
	}
	
	public static ByteBuf[] nulDelimiter()
    {
        return (new ByteBuf[] {
            Unpooled.wrappedBuffer(new byte[] {
                0
            })
        });
    }

    public static ByteBuf[] lineDelimiter()
    {
        return (new ByteBuf[] {
            Unpooled.wrappedBuffer(new byte[] {
                13, 10
            }), Unpooled.wrappedBuffer(new byte[] {
                10
            })
        });
    }
    public static ByteBuf[] protocolDelimiter()
    {
        return (new ByteBuf[] {
            Unpooled.wrappedBuffer(getDelimiter())
        });
    }

    private Delimiters()
    {
    }
}