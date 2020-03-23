"use strict";

const corsi = "Metodologie di programmazione per il Web, Reti 1, Paradigmi di programmazione, Sistemi operativi, Basi di dati e sistemi informativi";
let arrayDiCorsi = corsi.split(',');
let arrayAcronimi = [];

for (let i=0; i<arrayDiCorsi.length; i++) {
    let acronimo = "";
    let corso = arrayDiCorsi[i].trim();
    let arr = corso.split(' ');

    for (let j=0; j<arr.length; j++) {
        acronimo += arr[j].charAt(0);
    }
    arrayAcronimi[i] = acronimo.toUpperCase();
    arrayDiCorsi[i] = corso;
}

arrayAcronimi.sort();
arrayDiCorsi.sort();

for (let i=0; i<arrayDiCorsi.length; i++) {
    console.log(arrayAcronimi[i] + " : " + arrayDiCorsi[i]);
}
