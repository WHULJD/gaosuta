function openPopup() {
	document.getElementById('pop_up').style.left = (document.width - 390)/2 + 'px';
	document.getElementById('pop_up_bg').style.display = 'block';
	document.getElementById('pop_up').style.display = 'block';
}
function closePopup() {
	document.getElementById('pop_up_bg').style.display = 'none';
	document.getElementById('pop_up').style.display = 'none';
	document.getElementById('word_counter').value = '';
	document.getElementById('error').style.visibility = 'hidden';
	window.clearInterval(counter);
}
function countWords(maxLength) {
	counter = setInterval("wordsCounter("+maxLength+")", 100);
}
function wordsCounter(maxLength) {
	words = document.getElementById('word_counter').value;
	number = words.length; 
	max = parseInt(maxLength);
	if( number >= max) {
		document.getElementById('error').style.visibility = 'visible';
		document.getElementById('error').innerText = '哎呀，槽吐满了~';
		document.getElementById('word_counter').value = words.substring(0, max);
		document.getElementById('words').innerText = max;
	} else {
		document.getElementById('words').innerText = number;
		document.getElementById('error').style.visibility = 'hidden';	
	}
	
}
