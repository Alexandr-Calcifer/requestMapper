
'use strict';

class Counter{
  constructor(counter){
    this.counter = counter;
  }

  upCounter = () => {
    document.getElementById('likeCounter').innerHTML='Likes: ' + ++this.counter;
  }
}

const counter = new Counter(0);

const upCounter = () => {
  counter.upCounter();
};