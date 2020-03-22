"use strict";

const voti = [26, 20, 19, 21, 25, 24, 22];
let copiaVoti = Array.from(voti);

console.log("Voti prima:");  // stampo array prima di manipolarlo.
console.log(copiaVoti);

for (let nIterazioni=0; nIterazioni<2; nIterazioni++) {
    let votoMin = copiaVoti[0];
    let posMin = 0;
    let sommaVoti = copiaVoti[0];

    for (let i = 1; i < copiaVoti.length; i++) {
        if (copiaVoti[i] < votoMin) {
            votoMin = copiaVoti[i];
            posMin = i;
        }
        sommaVoti += copiaVoti[i];
    }
    let dualeVotoMin = 30-(votoMin-18);

    copiaVoti.splice(posMin, 1);
    copiaVoti.push(dualeVotoMin);
}

let sommaVotiPre = 0;
let sommaVotiPost = 0;

for(let i=0; i<voti.length; i++) {
    sommaVotiPre += voti[i];
    sommaVotiPost += copiaVoti[i];
}

console.log("Media: " + Math.round(sommaVotiPre/voti.length));

console.log("Voti dopo:");
console.log(copiaVoti);
console.log("Media: " + Math.round(sommaVotiPost/copiaVoti.length));