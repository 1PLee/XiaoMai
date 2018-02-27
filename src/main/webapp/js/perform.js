/**
 * Created by liyipeng on 2018/2/17.
 */

var name;
var time;
var venue;

$(document).ready(function () {

    showUser();
    name = sessionStorage.getItem("performName");
    time = sessionStorage.getItem("performTime");
    venue = sessionStorage.getItem("performVenue");
});