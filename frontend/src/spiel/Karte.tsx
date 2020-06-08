import React from 'react';
import './Karte.css'

interface Props {
  kartenId: string
  index?: number
}

interface State {
}

export default class Karte extends React.Component<Props, State> {

  render() {
    let style = this.props.index ?
        {transform: 'translateX(-' + (this.props.index * 86) + '%)'} :
        undefined

    return <img className={'karte'} src={'img/' + this.props.kartenId + '.svg'} alt={this.props.kartenId} style={style}/>;
  }
}
