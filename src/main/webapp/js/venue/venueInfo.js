/**
 * Created by liyipeng on 2018/3/9.
 */
var venueName;
var venueLocation;
var venueMail;
var capacity;
var description;

$(document).ready(function () {

    venueName = sessionStorage.getItem("venueName");
    $('#venueNameH1').html(venueName);
    $('#venueNameSmallP').html(venueName);
    $('#venueNameBigP').html(venueName);



    $.ajax({
        type:"get",
        url:"/Venue/getVenueInfo",
        contentType:'application/json;charset=utf-8',
        data:{"venue": venueName},
        success: function (result) {
            venueLocation = result.location;
            venueMail = result.mail;
            capacity = result.capacity;
            description = result.description;


            $('#venueNameSmallP').html(venueName + "<small>" + venueLocation + "</small>");//右上角小窗口

            $('#nameInput').val(venueName);
            $('#locationInput').val(venueLocation);
            $('#mailInput').val(venueMail);
            $('#capacityInput').val(capacity);
            $('#description').val(description);

        },
        error: function () {
            alert("getVenueInfo failed!")
        }
    })


});


/*场馆提交修改申请*/
$(document).on(
    {
        click:function () {
            var location = $('#locationInput').val();
            var mail = $('#mailInput').val();
            var capacity = $('#capacityInput').val();
            var description = $('#description').val();

            var changeVenueVO = {
                "name": venueName,
                "location": location,
                "capacity": capacity,
                "description": description,
                "mail": mail
            };

            $.ajax({
                type:"post",
                url:"/Venue/changeVenueInfo",
                contentType:'application/json;charset=utf-8',
                data: JSON.stringify(changeVenueVO),
                success:function (result) {
                    alert(result);
                    window.location.href = "./venueWelcome.html";
                },
                error:function () {
                    alert("changeVenueInfo failed!")
                }


            })


        }
    },'#submitModify'

);
