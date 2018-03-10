/**
 * Created by liyipeng on 2018/3/9.
 */

$(document).ready(function () {

    var venueName = sessionStorage.getItem("venueName");
    $('#venueNameSmallP').html(venueName);
    $('#venueNameBigP').html(venueName);

});



