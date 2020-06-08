import React from 'react';
import Karte from "./Karte";
import {KartenSetModel, StichModel} from "./model";
import KartenHand from "./KartenHand";
import AblageStapel from "./AblageStapel";

interface Props {
  karten: KartenSetModel
  stiche: StichModel[]
}

interface State {
}

export default class Spieler extends React.Component<Props, State> {

  render() {
    let karten = [];

    for (let i=0; i<this.props.karten.length; i++) {
      karten.push(<Karte key={i} kartenId={this.props.karten[i]} index={i}/>);
    }

    return <div className={'spieler'}>
      <AblageStapel stiche={this.props.stiche} />
      <KartenHand karten={this.props.karten} />
    </div>
  }
}
