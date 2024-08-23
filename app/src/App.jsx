import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login';
import Cadastro from './Cadastro';
import MainMenu from './MainMenu';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/cadastro" element={<Cadastro />} />
                <Route path="/mainmenu" element={<MainMenu />} />
            </Routes>
        </Router>
    );
}

export default App;
