/*if( //... Redirect for mobile device
 navigator.userAgent.match(/Android/i) || 
 navigator.userAgent.match(/webOS/i) || 
 navigator.userAgent.match(/iPhone/i) || 
 navigator.userAgent.match(/iPod/i) || 
 navigator.userAgent.match(/BlackBerry/i)
){
	location.href="lol.html";
}*/

// ... Choice of a random background
var x = Math.floor((Math.random() * 3) + 1);
if (x == 1)
	document.body.style.backgroundImage = "url('assets/img/sfd2a.jpg')";
else if (x == 2)
	document.body.style.backgroundImage = "url('assets/img/sfd2b.jpg')";
else //3
    document.body.style.backgroundImage = "url('assets/img/sfd2c.jpg')";

// ... Check credentials correctness
var state = "state=";
var url = document.location.toString(); 
var get = url.split(state); 
if(get[1] == "false")
{
	document.getElementById("lowBox").innerHTML = "<p style='font-weight: bold;'>Incorrect credentials, login using your username<br/> and your password to continue</p>";
	document.getElementById("usrname").style.boxShadow = "0 0 25px red";
	document.getElementById("password").style.boxShadow = "0 0 25px red";
}

function removeDefault(field) {
    if (field.value == field.defaultValue) {
        field.value = '';
    }
}

function setDefault(field) {
    if (field.value == '') {
        field.value = field.defaultValue;
    }
}

//Verify username and password before sending data to php authentication page
function checkDataToSend(form)
{
	var result = true;
	if(form.usrname.value == form.usrname.defaultValue || form.usrname.value.length < 3){
		result = false;
		form.usrname.style.boxShadow = "0 0 25px red";//Set edges color to red
	}
	else
		form.usrname.style.boxShadow = "0 0 0 red";
	if(form.password.value == form.password.defaultValue || form.password.value.length < 3){
		result = false;
		form.password.style.boxShadow = "0 0 25px red";
	}
	else
		form.password.style.boxShadow = "0 0 0 red";
	if(result == true){
		form.md5password.value = MD5(form.password.value);
        form.password.value = '';
	}
	return result;
}