/**
 * Created by liyipeng on 2018/3/11.
 */
var venueName;

$(document).ready(function () {

    venueName = sessionStorage.getItem("venueName");
    $('#venueNameSmallP').html(venueName);
    $('#venueNameBigP').html(venueName);
    loadPerformList();

});

function loadPerformList() {
    $('#performTable').DataTable( {
        "pageLength": 15,
        "ajax": {
            "url": "/Venue/getAllPerforms",
            "data": {
                "venue": venueName
            }
        },
        "columns": [
            { "data": "performId" },
            { "data": "performName" },
            { "data": "performTime" },
            { "data": "sellTickets" },
            { "data": "totalIncome" },
            { "data": "performType" }

        ],
        "createdRow": function (row, data, dataIndex) {


        },
        "columnDefs":[{
            "targets": 5,
            "createdCell":function (td, cellData, rowData, row, col) {

                switch (cellData) {
                    case 1:
                        $(td).html("演唱会");
                        break;
                    case 2:
                        $(td).html("音乐会");
                        break;
                    case 3:
                        $(td).html("话剧");
                        break;
                    case 4:
                        $(td).html("舞蹈");
                        break;
                    default:
                        $(td).html("体育比赛");
                }

            }
        }

        ]

    } );

}
