/**
 * Created by liyipeng on 2018/3/10.
 */

var venueName;

$(document).ready(function () {
     venueName = sessionStorage.getItem("venueName");

});



/*发布新的演出*/
$(document).on(
    {
        click:function () {
            var performName = $('#performName').val();
            var performTime = $('#datepicker').val();
            var performType = $('.select2 option:selected').text();
            var price = new Array();
            var seat = new Array();

            for(var i = 0;i<6;i++) {
                price.push($('#price'+(i+1)).val());
                seat.push($('#seat'+(i+1)).val());
            }

            var newPerformVO = {
                "venue": venueName,
                "name": performName,
                "time": performTime,
                "type": performType,
                "price": price,
                "seat": seat
            };

            $.ajax({
                type:'post',
                url:"/Perform/newPerform",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(newPerformVO),
                success:function (result) {
                    alert(result);
                    if(result == "操作成功"){
                        alert("添加演出成功");
                        window.location.href = './venueWelcome.html';
                    }

                },
                error:function () {
                    alert("newPerform failed!")
                }

            })

        }
    },'#newPerform'

);