import React from 'react';
import io from 'socket.io-client';
import './App.css';

function Square(props) {
  if (props.hit) {
    return (
      <button className="square red" onClick={props.onClick}>
        {props.value}
      </button>
    );
  }

  return (
    <button className="square" onClick={props.onClick}>
      {props.value}
    </button>
  );
}

class Board extends React.Component {
  renderSquare(i, j) {
    let index = i * 3 + j;
    let hit = this.props.hit ? this.props.hit.indexOf(index) > -1 : null;
    return <Square 
      key = {index}
      hit = {hit}
      value={this.props.squares[index]}
      onClick={()=> this.props.onClick(index)}
    />;
  }

  render() {
    var rows = [];
    
    for (let i = 0; i < 3; i++) {
      let row = [];
      for (let j = 0; j < 3; j++) {
        row.push(this.renderSquare(i, j)) ;
      }
      rows.push(
        <div key={i} className="board-row">
          {row}
        </div>
      );
    }

    return (
      <div>     
        {rows}
      </div>
    );
  }
}

class Game extends React.Component {
  constructor() {
    super();
    this.state = {
      history: [
        {
          squares: Array(9).fill(null)
        }
      ],
      stepNumber: 0,
      xIsNext: true,
      ascending: true,
      player: '',
      socket: io()
    }
  }

  componentWillMount() {
    var socket = this.state.socket;
    socket.emit('login');
    socket.on('login', (msg) => {
      if (this.state.player) {
        return;
      }
      this.setState({
        player: msg
      });
      
      var opposite = this.state.player === 'X' ? 'O' : 'X';
      socket.on('move' + opposite, (msg) => {
        console.log(msg);
        this.setState(msg);
      });
    });
  }

  toggle() {
    this.setState({
      ascending: !this.state.ascending
    });
  }

  handleClick(i) {
    if (this.state.player === 'X' && !this.state.xIsNext) {
      return;
    } else if (this.state.player === 'O' && this.state.xIsNext) {
      return;
    }

    const history = this.state.history.slice(0, this.state.stepNumber + 1);
    const current = history[history.length - 1];
    const squares = current.squares.slice();

    if (calculateWinner(squares) || squares[i]) {
      return;
    }

    squares[i] = this.state.xIsNext ? "X" : "O";

    const newState = {
      history: history.concat([
        {
          squares: squares
        }
      ]),
      stepNumber: history.length,
      xIsNext: !this.state.xIsNext
    };
    this.setState(newState);

    var socket = this.state.socket;
    socket.emit('move' + this.state.player, newState);
  }

  jumpTo(step) {
    this.setState({
      stepNumber: step,
      xIsNext: (step % 2) === 0
    });
  }

  render() {
    const history = this.state.history;
    const current = history[this.state.stepNumber];
    const result = calculateWinner(current.squares);
    const winner = result ? result.winner : null;

    let moves = history.map((step, move) => {
      const desc = move ? "Move #" + move : "Game start";
      const link = <a href="#" onClick={() => this.jumpTo(move)}>{desc}</a>;
      if (this.state.stepNumber === move) {
        return (
          <li key={move} className="bold">
            {link}
          </li>
        );
      } else {
        return (
          <li key={move}>
            {link}
          </li>
        );
      }

    });

    if (!this.state.ascending) {
      moves.reverse();
    }

    let status;
    if (winner) {
      status = "Winner: " + winner;
      this.state.socket.emit('over');
    } else {
      status = "Next player: " + (this.state.xIsNext ? "X" : "O");
    }

    let player = "You are:" +this.state.player;

    return (
      <div className="game">
        <div className="game-board">
          <Board 
            hit = {result ? result.hit : null}
            squares={current.squares}
            onClick={i => this.handleClick(i)}
          />
        </div>
        <div className="game-info">
          <div>{player}</div>
          <button onClick={() => this.toggle()}>toggle</button>
          <div>{status}</div>
          <ol>{moves}</ol>
        </div>
      </div> 
    );
  }
}

function calculateWinner(squares) {
  const lines = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],
    [2, 4, 6]
  ];

  for (let i = 0; i < lines.length; i++) {
    const [a, b, c] = lines[i];
    if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
      return {
        winner: squares[a],
        hit: [a, b, c]
      }
    }
  }

  return null;
}

export default Game;
