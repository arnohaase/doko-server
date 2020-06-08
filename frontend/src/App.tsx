import React from 'react';
import {KartenSetModel, KeineKarte, SpielerId, StichModel} from "./spiel/model";
import Spieler from "./spiel/Spieler";
import Stich from "./spiel/Stich";

function App() {
    let karten: KartenSetModel = ['kreuz-dame', KeineKarte, 'karo-as', 'kreuz-dame', 'pik-dame', 'herz-koenig']
    let stiche: StichModel[] = [
        {ersterSpieler: SpielerId.Drei, gewinner: SpielerId.Vier, karten: ['pik-zehn', 'pik-as', 'pik-as', 'pik-zehn']},
        {ersterSpieler: SpielerId.Vier, gewinner: SpielerId.Eins, karten: ['kreuz-zehn', 'kreuz-as', 'kreuz-as', 'kreuz-zehn']}
    ]

    /*
     * Layout: 0 ist links, 1 oben, 2 rechts, 3 unten
     */

    return (
        <div className="App">
            <Stich stich={stiche[0]} anzahlGespielt={4}/>
            <Spieler karten={karten} stiche={stiche} />
        </div>
    );
}

export default App;
