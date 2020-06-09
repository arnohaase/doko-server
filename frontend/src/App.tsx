import React from 'react';
import {
    KarteGespieltModel,
    KeineKarte,
    SpielerAktionArt,
    SpielerId, SpielModel,
} from "./spiel/model";
import Spiel from "./spiel/Spiel";

function App() {

    let spiel: SpielModel = {
        haende: [
            ['pik-koenig', 'pik-zehn', 'herz-as', 'kreuz-koenig', 'kreuz-zehn', 'karo-zehn', 'karo-as', 'karo-dame', 'herz-dame', 'pik-dame'],
            ['pik-as', 'herz-koenig', 'kreuz-koenig', 'kreuz-as', 'karo-koenig', 'karo-as', 'karo-dame', 'herz-dame', 'herz-zehn', 'herz-zehn'],
            ['pik-koenig', 'pik-zehn', 'herz-koenig', 'kreuz-zehn', 'karo-bube', 'herz-bube', 'pik-bube', 'kreuz-bube', 'pik-dame', 'kreuz-dame'],
            ['pik-as', 'herz-as', 'kreuz-as', 'karo-koenig', 'karo-zehn', 'karo-bube', 'herz-bube', 'pik-bube', 'kreuz-bube', 'kreuz-dame']
        ],
        journal: [
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Drei, karte: 'karo-as'} as KarteGespieltModel,
            {art: SpielerAktionArt.KarteGespielt, spieler: SpielerId.Vier, karte: 'karo-dame'} as KarteGespieltModel,
        ],
        stiche: [
            {ersterSpieler: SpielerId.Eins, gewinner: SpielerId.Eins, karten: ['herz-as', 'herz-koenig', 'herz-koenig', 'herz-as']},
            {ersterSpieler: SpielerId.Eins, gewinner: SpielerId.Zwei, karten: ['kreuz-koenig', 'kreuz-as', 'kreuz-zehn', 'kreuz-as']},
            {ersterSpieler: SpielerId.Zwei, gewinner: SpielerId.Zwei, karten: ['pik-as', 'pik-koenig', 'pik-as', 'pik-koenig']},
            {ersterSpieler: SpielerId.Zwei, gewinner: SpielerId.Drei, karten: ['kreuz-koenig', 'kreuz-dame', 'karo-zehn', 'kreuz-zehn']},
            {ersterSpieler: SpielerId.Drei, gewinner: SpielerId.Zwei, karten: ['pik-zehn', 'kreuz-dame', 'pik-zehn', 'herz-zehn']},
            {ersterSpieler: SpielerId.Zwei, gewinner: SpielerId.Zwei, karten: ['herz-dame', 'karo-bube', 'karo-bube', 'karo-as']},
            {ersterSpieler: SpielerId.Zwei, gewinner: SpielerId.Eins, karten: ['karo-koenig', 'herz-bube', 'herz-bube', 'karo-dame']},
            {ersterSpieler: SpielerId.Eins, gewinner: SpielerId.Zwei, karten: ['karo-zehn', 'herz-zehn', 'pik-bube', 'pik-bube']},
            {ersterSpieler: SpielerId.Zwei, gewinner: SpielerId.Eins, karten: ['karo-dame', 'kreuz-bube', 'kreuz-bube', 'herz-dame']},
            {ersterSpieler: SpielerId.Eins, gewinner: SpielerId.Eins, karten: ['pik-dame', 'karo-as', 'pik-dame', 'karo-koenig']},
        ]
    }

    /*
     * Layout: 0 ist links, 1 oben, 2 rechts, 3 unten
     */

    return (
        <Spiel spiel={spiel} />
    );
}

export default App;
