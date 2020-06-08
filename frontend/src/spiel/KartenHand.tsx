import React from 'react';
import Karte from "./Karte";
import {KartenSetModel} from "./model";

interface Props {
  karten: KartenSetModel
}

interface State {
}

export default class KartenHand extends React.Component<Props, State> {

  render() {
    let karten = [];

    for (let i=0; i<this.props.karten.length; i++) {
      karten.push(<Karte key={i} kartenId={this.props.karten[i]} index={i}/>);
    }

    return <div className={'kartenhand'}>
      {karten}
    </div>
  }
}
