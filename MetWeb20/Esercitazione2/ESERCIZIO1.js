let arrayOfTasks = [{descrizione: "dormire", importanza: "importante", condivisione: "privato", scadenza: "01/04/2020"}];

function AddNewTask(descrizione, importanza, condivisione, scadenza) {
    let task = {
        descrizione: descrizione.toLowerCase(),
        importanza: importanza.toLowerCase(),
        condivisione: condivisione.toLowerCase(),
        scadenza: scadenza.toLowerCase()
    };
    arrayOfTasks.push(task);
}

/**
 * @return {boolean}
 */
function RemoveTask(descrizione) {
    for (let i=0; i<arrayOfTasks.length; i++) {
        let task = arrayOfTasks[i];
        if (task.descrizione === descrizione) {
            arrayOfTasks.splice(i, 1);
            return true;
        }
    }
    return false;
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

function ListSortedTasks() {
    arrayOfTasks.sort(compare);
    console.log("... Stampa dei task in ordine alfabetico ...");
    console.log(arrayOfTasks);
}

function Menu() {
    console.log("... Menu del software ...");
    let scelta = readLineSync.question("Premere 1 per aggiungere un nuovo task...")
    if (scelta === 1) {
        AddNewTask("mangiare", "importante", "pubblico", "31/03/2020");
        console.log(arrayOfTasks);
    }
}
Menu();