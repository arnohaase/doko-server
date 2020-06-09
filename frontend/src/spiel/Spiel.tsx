import React from 'react';
import {KeineKarte, LaufendesSpielModel, SpielModel} from "./model";
import './Spiel.css';
import Journal from "./Journal";
import Tisch from "./Tisch";

interface Props {
  spiel: SpielModel
}

interface State {
  journalIndex: number,
  laufendesSpiel: LaufendesSpielModel
}

export default class Spiel extends React.Component<Props, State> {

  constructor(props: Props) {
    super(props);
    this.state = Spiel.stateFromProps(props);

    this.onNext = this.onNext.bind(this);
  }

  private static stateFromProps(props: Props) {
    return {
      laufendesSpiel: {
        haende: JSON.parse(JSON.stringify(props.spiel.haende)),
        abgelegteStiche: [],
        aktStich: props.spiel.stiche[0],
        aktStichAnzGespielt: 0,
      } as LaufendesSpielModel,
      journalIndex: -1,
    } as State;
  }


  componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>, snapshot?: any) {
    if (prevProps.spiel !== this.props.spiel) {
      this.setState(Spiel.stateFromProps(this.props));
    }
  }

  componentDidMount() {
    document.addEventListener('keydown', this.onNext, false);
  }

  componentWillUnmount() {
    document.removeEventListener('keydown', this.onNext, false);
  }

  private onNext(event: KeyboardEvent) {
    switch(event.key) {
      case 'Escape':
        this.setState(Spiel.stateFromProps(this.props));
        break;
      case ' ':
        const spiel = this.state.laufendesSpiel;
        let journalIndex = this.state.journalIndex;

        if (spiel.abgelegteStiche.length === this.props.spiel.stiche.length) {
          return;
        }

        if (spiel.aktStichAnzGespielt === 4) {
          spiel.abgelegteStiche.push(spiel.aktStich);
          spiel.aktStich = this.props.spiel.stiche[spiel.abgelegteStiche.length];
          spiel.aktStichAnzGespielt = 0;
        }
        else {
          const spieler = (spiel.aktStich.ersterSpieler + spiel.aktStichAnzGespielt) % 4;
          const idxKarte = spiel.haende[spieler].indexOf(spiel.aktStich.karten[spiel.aktStichAnzGespielt]);
          spiel.haende[spieler][idxKarte] = KeineKarte;

          journalIndex += 1;
          spiel.aktStichAnzGespielt += 1;
        }
        this.setState({
          laufendesSpiel: spiel,
          journalIndex: journalIndex
        } as State);
        break;
    }
  }

  render() {
    return <div className="spiel">
      <Journal aktionen={this.props.spiel.journal} aktIndex={this.state.journalIndex}/>
      <Tisch spiel={this.state.laufendesSpiel} />
    </div>
  }
}
