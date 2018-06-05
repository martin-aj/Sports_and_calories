/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
jQuery(document).ready(function ($) {
    $("#startOfActivity").datepicker();
    $("#startOfActivity").datepicker("option", "changeMonth", true);
    $("#startOfActivity").datepicker("option", "changeYear", true);
    $("#startOfActivity").datepicker("option", "showAnim", "size");        
});