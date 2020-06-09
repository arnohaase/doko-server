import React from 'react';
import Karte from "./Karte";
import {StichModel} from "./model";

interface Props {
  stiche: StichModel[]
}

interface State {
}

export default class AblageStapel extends React.Component<Props, State> {

  render() {
    let stiche=[];

    for (let i=0; i<this.props.stiche.length; i++) {
      const stich = this.props.stiche[i];

      stiche.push(
          <div key={i} className={'ablagestich ablagestich-' + i}>
              <div className={'ablagestich-karte'}><Karte kartenId={stich.karten[0]} index={0}/></div>
              <div className={'ablagestich-karte'}><Karte kartenId={stich.karten[1]} index={1}/></div>
              <div className={'ablagestich-karte'}><Karte kartenId={stich.karten[2]} index={2}/></div>
              <div className={'ablagestich-karte'}><Karte kartenId={stich.karten[3]} index={3}/></div>
          </div>)
    }

    return <div className={'ablagestapel'}>
      { stiche }
    </div>
  }
}
