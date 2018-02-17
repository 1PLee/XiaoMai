/**
 * Created by liyipeng on 2018/2/17.
 */


$(document).ready(function() {

    $('#performTable').DataTable( {
        "pageLength": 25,
        "ajax": "/Perform/getAllPerforms",
        "columns": [
            { "data": "name" },
            { "data": "time" },
            { "data": "venue" },
            { "data": "priceMin" },
            { "data": "type" }

        ],
        "columnDefs":[{
            "targets": 0,
            "createdCell":function (td, cellData, rowData, row, col) {
                $(td).addClass("performName")
                $(td).click(function(){
                    window.document.location = "pages/perform.html?performID="+rowData['performID']
                });
            }
        },
            {
                "targets": 2,
                "createdCell":function (td, cellData, rowData, row, col) {
                    $(td).addClass("performVenue")
                    $(td).click(function(){
                        window.document.location = "pages/venue.html?venue="+rowData['venue']
                    });
                }
            }
        ]
    } );
} );