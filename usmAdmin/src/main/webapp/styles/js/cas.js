/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

$(document).ready(function(){
    //focus username field
    $("input:visible:enabled:first").focus();
    /* 
     * Using the JavaScript Debug library, you may issue log messages such as: 
     * debug.log("Welcome to Central Authentication Service");
     */
});
function checkform(target){
    var $target = $(target);
    var username = $target.find('#username').val(),password = $target.find('#password').val();
    var userRule = /^[0-9 A-Za-z \- \_ \,]+$/i,passwordRule = /^[0-9 A-Za-z]+$/i;
    $('#msg.errors').hide();
    if(!username){
        $('#msg.errors').show().text('请输入用户名');
        return false;
    }else{
        if(!userRule.test(username)){
            $('#msg.errors').show().text('用户名格式错误');
            return false;
        }
    }
    if(!password){
        $('#msg.errors').show().text('请输入密码');
        return false;
    }else{
        if(!passwordRule.test(password)){
            $('#msg.errors').show().text('密码格式错误');
            return false;
        }
    }
    console.log(username,password)
    return true;
}