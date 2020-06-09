import React from 'react';
import Karte from "./Karte";
import {KartenId, KartenSetModel, SpielerId, StichModel} from "./model";
import KartenHand from "./KartenHand";
import AblageStapel from "./AblageStapel";

interface Props {
  spielerId: SpielerId,
  karten: KartenSetModel,
  stiche: StichModel[],
}

interface State {
}

function punkte(karte: KartenId) {
  switch(karte.substring(karte.indexOf('-') + 1)) {
    case 'bube': return 2;
    case 'dame': return 3;
    case 'koenig': return 4;
    case 'zehn': return 10;
    case 'as': return 11;
    default: return 0;
  }
}

export default class Spieler extends React.Component<Props, State> {

  render() {
    let p = 0;
    this.props.stiche.forEach(stich => p +=
        punkte(stich.karten[0]) +
        punkte(stich.karten[1]) +
        punkte(stich.karten[2]) +
        punkte(stich.karten[3])
    );

    let karten = [];
    for (let i=0; i<this.props.karten.length; i++) {
      karten.push(<Karte key={i} kartenId={this.props.karten[i]} index={i}/>);
    }

    return <div className={'spieler'}>
      <AblageStapel stiche={this.props.stiche} />
      <div className={'spieler-hauptbereich'}>
        <div className={'spieler-titel'}>
          <div className={'spieler-punkte'}>{p} Punkte</div>
          Spieler {this.props.spielerId + 1}
        </div>
        <KartenHand karten={this.props.karten} />
      </div>
    </div>
  }
}
