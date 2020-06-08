import React from 'react';
import Karte from "./Karte";
import {StichModel} from "./model";

interface Props {
  stich: StichModel
  anzahlGespielt: number
}

interface State {
}

export default class Stich extends React.Component<Props, State> {

  private classNameFor(idx: number) {
    return 'stich-spieler-' + (this.props.stich.ersterSpieler + idx) % 4;
  }

  render() {
    let karten = [];

    for (let i=0; i<this.props.anzahlGespielt; i++) {
      karten.push(
          <div key={i} className={'stich-spieler'}>
            <div key={i} className={this.classNameFor(i)}>
              <Karte kartenId={this.props.stich.karten[i]} />
            </div>
          </div>
      );
    }

    return <div className={'stich'}>
      {karten}
    </div>
  }
}
