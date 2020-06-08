import React from 'react';
import {LaufendesSpielModel} from "./model";
import Spieler from "./Spieler";
import Stich from "./Stich";

interface Props {
  spiel: LaufendesSpielModel
}

interface State {
}

export default class Tisch extends React.Component<Props, State> {

  render() {
    let spieler = [];

    for (let i=0; i<4; i++) {
      let stiche = this.props.spiel.abgelegteStiche.filter(stich => stich.gewinner === i);

      spieler.push(
          <div className={'tisch-spieler tisch-spieler-' + i} key={i}>
            <Spieler karten={this.props.spiel.haende[i]} stiche={stiche} />
          </div>
      );
    }

    return <div className={'tisch'}>
      <div className={'tisch-stich'}>
        <Stich stich={this.props.spiel.aktStich} anzahlGespielt={this.props.spiel.aktStichAnzGespielt} />
      </div>
      {spieler}
    </div>
  }
}
