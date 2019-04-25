jQuery(document).ready(function(){
    jQuery('#myTable1').gvChart({
        chartType: 'AreaChart',
        gvSettings: {
            vAxis: {title: 'No of players'},
            hAxis: {title: 'Month'},
            width: 720,
            height: 300,
        }
    });


    jQuery('#myTable2').gvChart({
        chartType: 'LineChart',
        gvSettings: {
            vAxis: {title: 'No of players'},
            hAxis: {title: 'Month'},
            width: 720,
            height: 300,
        }
    });

    jQuery('#myTable3').gvChart({
        chartType: 'BarChart',
        gvSettings: {
            vAxis: {title: 'No of players'},
            hAxis: {title: 'Month'},
            width: 720,
            height: 300,
        }
    });


    jQuery('#myTable4').gvChart({
        chartType: 'ColumnChart',
        gvSettings: {
            vAxis: {title: 'No of players'},
            hAxis: {title: 'Month'},
            width: 720,
            height: 300,
        }
    });

    jQuery('#myTable5').gvChart({
        chartType: 'PieChart',
        gvSettings: {
            vAxis: {title: 'No of players'},
            hAxis: {title: 'Month'},
            width: 720,
            height: 300,
        }
    });
});