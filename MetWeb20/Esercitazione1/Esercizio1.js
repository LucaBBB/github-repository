"use strict";

const voti = [18, 20, 22, 25, 30, 26];

let votiCopy = Array.from(voti);

for (let i=0; i<2; i++) {
    let votoPeggiore = Math.min(...votiCopy);
    let duale = (30-(votoPeggiore-18));

    let posPeggiore = votiCopy.indexOf(votoPeggiore);
    votiCopy.splice(posPeggiore, 1);
    votiCopy.push(duale);
}

let sommaVoti = 0;
for(let i=0; i<voti.length; i++) {
    console.log(i+1 + "o voto: " + voti[i]);
    sommaVoti += voti[i];
}

let sommaVotiCopy = 0;
for(let i=0; i<votiCopy.length; i++) {
    console.log(i+1 + "o voto migliorato: " + votiCopy[i]);
    sommaVotiCopy += votiCopy[i];
}

let mediaVotiArr = Math.round(sommaVoti/voti.length);
let mediaVotiCopyArr = Math.round(sommaVotiCopy/voti.length);

console.log("La media dei voti e': " + mediaVotiArr);
console.log("La media dei voti migliorata e': " + mediaVotiCopyArr);