/**
 * Created by liyipeng on 2018/3/19.
 */


$(document).ready(function () {

    loadUnSettlePerform();
});




function loadUnSettlePerform() {
    var unSettlePerforms = new Array();
    var onePerform = null;

    $.ajax({
        type:"get",
        url:"/Manager/getAllUnSettlePerform",
        contentType:'application/json;charset=utf-8',
        success:function (result) {

            for(var i =0;i<result.length;i++){
                onePerform = new Array();
                onePerform["performID"] = result[i]["performID"];

                onePerform["name"] = result[i]["name"];
                onePerform["time"] = result[i]["time"];
                onePerform["venue"] = result[i]["venue"];
                onePerform["totalTicketNum"] = result[i]["performIncomeVO"]["totalTicketNum"];
                onePerform["backTicketNum"] = result[i]["performIncomeVO"]["backTicketNum"];
                onePerform["totalIncome"] = result[i]["performIncomeVO"]["totalIncome"];
                //onePerform["pay"] = "结算";
                unSettlePerforms.push(onePerform);

            }

            loadUnSettleTable(unSettlePerforms);

        },
        error:function () {
            alert("getAllUnSettlePerform failed!");
        }


    })


}

function loadUnSettleTable(performs) {

    $('#unSettlePerformTable').DataTable( {
        "pageLength": 15,
        "data": performs,

        "columns": [
            { "data": "performID" },
            { "data": "name" },
            { "data": "time" },
            { "data": "venue" },
            { "data": "totalTicketNum" },
            { "data": "backTicketNum" },
            { "data": "totalIncome" },
            { "data":  null}

        ],
        "columnDefs":[{
            "targets": 7,
          /*  "render":function (data, type, row) {
                var payBtn = "<button class='btn btn-info'>结算</button>";

                return payBtn;
            },*/
            "createdCell":function (td, cellData, rowData, row, col) {
                $(td).html("<button class='btn btn-info'>结算</button>");
                $(td).click(function () {
                    alert(rowData["totalIncome"]);
                    var performIncomeVO = {"totalIncome": rowData["totalIncome"]};
                    var performID = rowData["performID"];
                    var performTime = rowData["time"];

                    var performVO = {
                        "performID": performID,
                        "time": performTime,
                        "performIncomeVO": performIncomeVO
                    };

                    $.ajax({
                        type: "post",
                        url: "/Manager/payVenueIncome",
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(performVO),
                        success: function (result) {
                            alert(result);
                        },
                        error: function () {
                            alert("payVenueIncome failed!");
                        }

                    });

                })

            }
        }

        ]

    } );


}