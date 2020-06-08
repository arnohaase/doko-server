
export enum SpielerId {
	Eins,
	Zwei,
	Drei,
	Vier
}
export type KartenId = string
export type KartenSetModel = KartenId[]

export const KeineKarte: KartenId = "leer"

export interface StichModel {
	ersterSpieler: SpielerId
	karten: string[]
	gewinner: SpielerId
}

export enum SpielerAktionArt {
	KarteGespielt = 'KarteGespielt'
}

export interface SpielerAktionModel {
	art: SpielerAktionArt
	spieler: SpielerId
}

export interface KarteGespieltModel extends SpielerAktionModel {
	karte: KartenId
}


export interface SpielModel {
	journal: SpielerAktionModel[]
	stiche: StichModel[]
}
