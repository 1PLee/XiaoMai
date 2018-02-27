/**
 * Created by liyipeng on 2018/2/17.
 */


$(document).ready(function() {
    showUser(); //检查用户是否登录

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
                    sessionStorage.setItem("performName", rowData['name']);
                    sessionStorage.setItem("performTime", rowData['time']);
                    sessionStorage.setItem("performVenue", rowData['venue']);
                    window.document.location = "perform.html?performID="+rowData['performID']
                });
            }
        },
            {
                "targets": 2,
                "createdCell":function (td, cellData, rowData, row, col) {
                    $(td).addClass("performVenue")
                    $(td).click(function(){
                        window.document.location = "venue.html?venue="+rowData['venue']
                    });
                }
            }
        ]
    } );
} );