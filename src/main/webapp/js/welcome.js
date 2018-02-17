/**
 * Created by liyipeng on 2018/2/17.
 */

$(document).ready(function() {
    $('#performTable').DataTable( {
        "ajax": "/Perform/getAllPerforms",
        "columns": [
            { "data": "name" },
            { "data": "time" },
            { "data": "venue" },
            { "data": "priceMin" },
            { "data": "type" }

        ]
    } );
} );