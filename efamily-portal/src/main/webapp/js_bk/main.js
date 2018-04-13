var HIDDEN = 'hidden';

$('.dropdown').hoverIntent(function () {
    $(this).find('.dropdown-menu').removeClass(HIDDEN);
}, function () {
    $(this).find('.dropdown-menu').addClass(HIDDEN);
});
function doWaypoint() {}
function destroyWaypoint() {}
function attachClass(className) {
    $(document.documentElement).removeClass(function (index, oldClass) {
        var old = oldClass.match(/\w+-view/g);
        if (old) {
            old = old.join(' ');
        }
        return old;
    }).addClass(className).removeClass('loading');

}
function detectDevice() {
    var width = $(window).width(),
        index,
        DEVICE = ['desktop-view', 'mobile-view', 'mobile-view'];
    if (width > 1024) {
        index = 0;
        doWaypoint && doWaypoint()
    } else if (width > 768) {
        index = 1;
        destroyWaypoint && destroyWaypoint();
    } else {
        index = 2;
        destroyWaypoint && destroyWaypoint();
    }
    attachClass(DEVICE[index]);
}
function deviceMonitor() {
    $(window).bind('resize', function () {
        detectDevice();
    });
    detectDevice();
}

function responsiveNav() {
    $('#mobile-menu-trigger').click(function () {
        $('#J_ResponsiveNav').slideDown();
    });
    $('#J_ResponsiveNav').delegate('.responsive-nav-close', 'click', function (ev) {
        ev.preventDefault();
        $('#J_ResponsiveNav').hide();
    });
    $('#J_ResponsiveNav').delegate('.dropdown>a', 'click', function (ev) {
        ev.preventDefault();

        if ($(this).parent().hasClass('dropped')) {
            $(this).parent().removeClass('dropped');
        } else {
            $(this).parent().addClass('dropped');
        }
    });
}
responsiveNav();

