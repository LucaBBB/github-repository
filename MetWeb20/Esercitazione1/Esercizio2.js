"use strict";

const corsi = "Metodologie di programmazione per il Web, Reti 1, Paradigmi di programmazione, Sistemi operativi, Basi di dati e sistemi informativi";
let arrayDiCorsi = corsi.split(','); // inserisco in arrayDiCorsi ogni corso, separando ciascuno grazie alla virgola.
let arrayAcronimi = []; //creo l'array per gli acronimi.

let i=0;
for (const c of arrayDiCorsi) {
    let acronimo = "";
    let corso = c.trim(); // prendo un elemento dall'array dei corsi (lo chiamo corso) e tolgo tutti gli spazi inutili.
    let arr = corso.split(' '); // metto in un array (arr) ogni parola di corso splittandolo grazie allo spazio tra le parole.

    for (const a of arr) {
        acronimo += a.charAt(0); // prendo il primo carattere di ogni parola del corso.
    }
    arrayDiCorsi[i] = corso;    // aggiungo il corso senza spazi e a posto
    arrayAcronimi.push(acronimo.toUpperCase()); // metto maiuscolo ogni carattere dell'acronimo.
    i++;
}


arrayDiCorsi.sort();  // ordino i corsi.
arrayAcronimi.sort(); // ordino gli acronimi.

for (let i=0; i<arrayDiCorsi.length; i++) {
    console.log(arrayAcronimi[i] + " : " + arrayDiCorsi[i]);
}