import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./login";
// import Main from "./main";
import React from "react";
// import Manager from "./manager";
import "./App.css";
import HeaderOne from "./HeaderOne";
import HeadMeta from "./HeadMeta";


function App(){
  return (
    <div className="App">
    <BrowserRouter>
      <Routes>
      
      {/* <Route exact path='/' element={< Login />}></Route> */}
      {/* <Route exact path='/employee/:id' element={< Main />}></Route>
      <Route exact path='/manager/:id' element={< Manager />}></Route> */}
      <Route exact path='/head' element={< HeaderOne />}></Route>
      </Routes>
      </BrowserRouter>
      
      </div>
    );
}

export default App;