//$(document).ready(function() {
	
	//changeSource(movieId);
	
	window.changeSource = function(movieId) {
		let url = "/movie/find-movie/" + movieId + "/84a95644-3559-4848-889b-2a207d771b2f";
		var video = document.getElementById('player');
		video.src = url;
	   //video.play();
	}
//});