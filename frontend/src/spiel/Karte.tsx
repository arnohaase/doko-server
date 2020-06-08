import React from 'react';
import './Karte.css'

interface Props {
  kartenId: string
  index: number
}

interface State {
}

export default class Karte extends React.Component<Props, State> {

  render() {
    let className = 'karte  karte-' + this.props.index;
    return <img className={className} src={'img/' + this.props.kartenId + '.svg'} alt={this.props.kartenId} />;
  }
}
