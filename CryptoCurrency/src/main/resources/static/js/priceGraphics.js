jQuery(document).ready(function(){
    jQuery('#PriceGraphics').delay(1000).gvChart({
        chartType: 'AreaChart',
        gvSettings: {
            vAxis: {title: 'Price USD'},
            hAxis: {title: 'days'},
            width: 1420,
            height: 400,
        }
    });
    jQuery('#TradeGraphics').delay(1000).gvChart({
        chartType: 'AreaChart',
        gvSettings: {
            vAxis: {title: 'Trade Count'},
            hAxis: {title: 'days'},
            width: 1420,
            height: 400,
        }
    });
    jQuery('#VolumeGraphics').delay(1000).gvChart({
        chartType: 'AreaChart',
        gvSettings: {
            vAxis: {title: 'Volume'},
            hAxis: {title: 'days'},
            width: 1420,
            height: 400,
        }
    });
});

