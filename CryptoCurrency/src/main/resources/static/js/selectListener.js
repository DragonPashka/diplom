jQuery(document).ready(function() {
    jQuery("#SelectCurrencies").change(function () {

        if ($(this).val()!= ""){
            jQuery("#Graphics").load("/history #Graphics", {"address": $(this).val()});

            jQuery('#PriceGraphics').gvChart({
                chartType: 'AreaChart',
                gvSettings: {
                    vAxis: {title: 'Price USD'},
                    hAxis: {title: 'days'},
                    width: 1420,
                    height: 400,
                }
            });

            jQuery('#TradeGraphics').gvChart({
                chartType: 'AreaChart',
                gvSettings: {
                    vAxis: {title: 'Trade Count'},
                    hAxis: {title: 'days'},
                    width: 1420,
                    height: 400,
                }
            });
            jQuery('#VolumeGraphics').gvChart({
                chartType: 'AreaChart',
                gvSettings: {
                    vAxis: {title: 'Volume'},
                    hAxis: {title: 'days'},
                    width: 1420,
                    height: 400,
                }
            });

            location.reload();
        }


    }).change();


    jQuery("#AddCurrencyToGraphics").change(function () {

        if ($(this).val()!= ""){
            jQuery("#Graphics").load("/addCurrencyToGraphics #Graphics", {"address": $(this).val()});

            jQuery('#PriceGraphics').gvChart({
                chartType: 'AreaChart',
                gvSettings: {
                    vAxis: {title: 'Price USD'},
                    hAxis: {title: 'days'},
                    width: 1420,
                    height: 400,
                }
            });

            jQuery('#TradeGraphics').gvChart({
                chartType: 'AreaChart',
                gvSettings: {
                    vAxis: {title: 'Trade Count'},
                    hAxis: {title: 'days'},
                    width: 1420,
                    height: 400,
                }
            });
            jQuery('#VolumeGraphics').gvChart({
                chartType: 'AreaChart',
                gvSettings: {
                    vAxis: {title: 'Volume'},
                    hAxis: {title: 'days'},
                    width: 1420,
                    height: 400,
                }
            });

            location.reload();
        }


    }).change();


});