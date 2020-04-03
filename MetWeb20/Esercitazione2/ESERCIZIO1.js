"use strict";

let arrayOfTasks = [];

const readlineSync = require('readline-sync');

function showMenu() {
    console.log("... Menu ...");
    console.log("... premere 1 per inserire un nuovo task ...");
    console.log("... premere 2 per rimuovere un task ...");
    console.log("... premere 3 per rimuovere un task per giorno ...");
    console.log("... premere 4 per visualizzare tutti i task ...");
    return parseInt(readlineSync.question("Inserisci il numero riferito alla scelta che vuoi effettuare: "));
}

function addNewTask(descr, imp="non importante", cond="privato", scad) {
    let newTask = {
        descrizione: descr.toLowerCase(),
        importanza: imp.toLowerCase(),
        condivisione: cond.toLowerCase(),
        scadenza: scad
    };
    arrayOfTasks.push(newTask)
}

function removeTask() {
    if (arrayOfTasks.length === 0) {
        console.log("Impossibile rimuovere!");
        console.log("Nesson task salvato!");
        return 0;
    }
    else {
        let descrizione = readlineSync.question("Inserire la descrizione del task da rimuovere: ");
        for (const task of arrayOfTasks) {
            if (task.descrizione === descrizione)
                arrayOfTasks.splice(task);
        }
    }
}

function removeTaskByDate() {
    if (arrayOfTasks.length === 0) {
        console.log("Impossibile rimuovere!");
        console.log("Nessun task salvato!");
        return -1;
    }
    else {
        let i = 0;
        let rimossi = 0;
        while (i < arrayOfTasks.length) {
            let data = new Date(readlineSync.question("Inserire la data dei task da rimuovere: "));
            let dataTask = Date.parse(arrayOfTasks[i].scadenza);
            if (data === dataTask) {
                arrayOfTasks.splice(arrayOfTasks[i], 1);
                rimossi++;
            }
            else
                i++;
        }
        return rimossi;
    }
}

function compare(a, b) {
    if (a.descrizione < b.descrizione) {
        return -1;
    }
    if (a.descrizione > b.descrizione) {
        return 1;
    }
    return 0;
}

function listSortedTasks() {
    if (arrayOfTasks.length === 0)
        console.log("Nessun task presente!");
    else {
        arrayOfTasks.sort(compare);
        console.log("... Stampa dei task in ordine alfabetico ...");
        for (const task of arrayOfTasks)
            console.log(task);
    }
}

function insertData(scelta) {
    if (scelta === 1) {
        let descr = readlineSync.question("Inserisci la descrizione del nuovo task: ");
        let imp;
        if (readlineSync.question("E' importante (si/no): ") === "si")
            imp = "importante";
        else
            imp = "non importante";
        let cond = readlineSync.question("E' privato o condiviso?: ");
        let scad = new Date(readlineSync.question("Inserisci la scadenza del nuovo task (YYYY-MM-DD): "));
        if (cond === '')
            addNewTask(descr, imp, scad);
        else
            addNewTask(descr, imp, cond, scad);
    }
}

let scelta = 0;
while (true) {
    do {
        scelta = showMenu();
    }
    while (scelta < 1 || scelta > 4);

    switch(scelta) {
        case 1:
            insertData(1);
            break;

        case 4:
            listSortedTasks();
            break;

        /**
         case 2:
         removeTask();
         break;

         case 3:
         console.log("... Inserimento nuovo task ...");

         break;

         case 4:
         listSortedTasks();
         break;

         case 5:
         console.log("Arrivederci!");
         break;
         }*/
    }
}