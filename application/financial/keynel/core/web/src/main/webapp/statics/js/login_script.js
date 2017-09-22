function getRootPath(){

    // 获取当前网址，如： http://localhost:80/page/index.html
    var curPath=window.document.location.href;

    // 获取主机地址之后的目录，如： /page/index.jsp
    var pathName=window.document.location.pathname;

    // 获取主机地址，如： http://localhost:80
    var posIndex=curPath.indexOf(pathName);
    var localhostPath=curPath.substring(0,posIndex);

//    //获取带"/"的项目名，如：/page
//    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
//    alert(localhostPaht+projectName);

    return localhostPath;
}

$(function(){
	
	// Checking for CSS 3D transformation support
	$.support.css3d = supportsCSS3D();

	var formContainer = $('#formContainer');
	
	// Listening for clicks on the ribbon links
	$('.flipLink').click(function(e){

		// Flipping the forms
		formContainer.toggleClass('flipped');
		
		// If there is no CSS3 3D support, simply
		// hide the login form (exposing the recover one)
		if(!$.support.css3d){
			$('#login').toggle();
		}
		e.preventDefault();
	});

	// A helper function that checks for the 
	// support of the 3D CSS3 transformations.
	function supportsCSS3D() {
		var props = [
			'perspectiveProperty', 'WebkitPerspective', 'MozPerspective'
		], testDom = document.createElement('a');

		for(var i=0; i<props.length; i++){
			if(props[i] in testDom.style){
				return true;
			}
		}

		return false;
	}
});

$(document).ready(function(){
    $("#login").submit(function(){
        window.location = "http://www.labangme.com";
        var random = Math.random();
        if ($("#loginEmail").val() == "0" && $("#loginPassword").val() == "0") {
            var url = getRootPath() + "/datetimepicker.html";
            alert(url + "   " + random);
            top.location="/datetimepicker.html";
        }
    })
});