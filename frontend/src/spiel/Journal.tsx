import React from 'react';
import {KarteGespieltModel, SpielerAktionArt, SpielerAktionModel} from "./model";

interface Props {
  aktionen: SpielerAktionModel[],
  aktIndex: number
}

interface State {
}

export default class Journal extends React.Component<Props, State> {

  private static format(aktion: SpielerAktionModel) {
    let result = 'Spieler ' + (aktion.spieler + 1);
    switch (aktion.art) {
      case SpielerAktionArt.KarteGespielt:
        let gespielt = aktion as KarteGespieltModel;
        result += ' spielt ' + gespielt.karte;
        break;
    }
    return result;
  }

  render() {
    let entries = [];

    for (let i=0; i<this.props.aktionen.length; i++) {
      let className = 'journal-eintrag';
      if (i === this.props.aktIndex) {
        className += ' journal-eintrag-aktuell';
      }

      entries.push(
          <div key={i} className={className}>
            { Journal.format(this.props.aktionen[i]) }
          </div>
      );
    }

    return <div className={'journal'}>
      {entries}
    </div>
  }
}
