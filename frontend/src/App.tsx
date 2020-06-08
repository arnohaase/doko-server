import React from 'react';
import {KartenSetModel, KeineKarte, LaufendesSpielModel, SpielerId, StichModel} from "./spiel/model";
import Tisch from "./spiel/Tisch";

function App() {
    let karten: KartenSetModel = ['kreuz-dame', KeineKarte, 'karo-as', 'kreuz-dame', 'pik-dame', 'herz-koenig']
    let stiche: StichModel[] = [
        {ersterSpieler: SpielerId.Drei, gewinner: SpielerId.Vier, karten: ['pik-zehn', 'pik-as', 'pik-as', 'pik-zehn']},
        {ersterSpieler: SpielerId.Vier, gewinner: SpielerId.Eins, karten: ['kreuz-zehn', 'kreuz-as', 'kreuz-as', 'kreuz-zehn']},
        {ersterSpieler: SpielerId.Drei, gewinner: SpielerId.Vier, karten: ['pik-zehn', 'pik-as', 'pik-as', 'pik-zehn']},
        {ersterSpieler: SpielerId.Vier, gewinner: SpielerId.Eins, karten: ['herz-zehn', 'herz-as', 'herz-as', 'herz-zehn']},
        {ersterSpieler: SpielerId.Vier, gewinner: SpielerId.Zwei, karten: ['kreuz-dame', 'herz-zehn', 'kreuz-dame', 'herz-zehn']}
    ]

    let spiel: LaufendesSpielModel = {
        aktStich: {
            ersterSpieler: SpielerId.Zwei, gewinner: SpielerId.Eins, karten: ['herz-bube', 'karo-bube', 'pik-bube', 'kreuz-bube']
        },
        aktStichAnzGespielt: 3,

        haende: [
            ['kreuz-dame', KeineKarte, 'karo-as', 'kreuz-dame', 'pik-dame', 'herz-koenig'],
            ['kreuz-dame', KeineKarte, 'karo-as', 'kreuz-dame', 'pik-dame', 'herz-koenig'],
            ['kreuz-dame', KeineKarte, 'karo-as', 'kreuz-dame', 'pik-dame', 'herz-koenig'],
            ['kreuz-dame', KeineKarte, 'karo-as', 'kreuz-dame', 'pik-dame', 'herz-koenig']
        ],
        abgelegteStiche: stiche
    }


    /*
     * Layout: 0 ist links, 1 oben, 2 rechts, 3 unten
     */

    return (
        <div className="App">
            <Tisch spiel={spiel} />

            {/*<Stich stich={stiche[0]} anzahlGespielt={4}/>*/}
            {/*<Spieler karten={karten} stiche={stiche} />*/}
        </div>
    );
}

export default App;
