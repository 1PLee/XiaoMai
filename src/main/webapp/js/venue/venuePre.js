/**
 * Created by liyipeng on 2018/2/27.
 */

var venueName;
var venueLocation;
var venueDescription;
var venueCapacity;

$(document).ready(function () {
   
    showUser();
    venueName = sessionStorage.getItem("venueName");
    $('.name').html(venueName);
    loadVenue();
});


/*加载场馆信息*/
function loadVenue() {
    
   $.ajax({
      
       type:"get",
       url:"/Venue/getVenueInfo",
       contentType:'application/json;charset=utf-8',
       data:{"venue":venueName},
       success:function (result) {

           venueDescription = result.description;
           venueLocation = result.location;
           venueCapacity = result.capacity;
           $('#desP').html(venueDescription);
           $('#locationP').html(venueLocation);
           $('#capacityP').html(venueCapacity+"人")
       },
       error:function () {
           alert("getVenueInfo failed");
       }
   });


}