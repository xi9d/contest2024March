import React from "react"
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Navbar from "./Components/Navbar";
import Footer from "./Components/Footer";
import Main from "./Pages/Main";
import ViewMechanic  from "./Pages/ViewMechanic"
import {AuthProvider} from "./Context/AuthContext";
function App() {
  return (
    <div className="App">
      <AuthProvider>
      <BrowserRouter>
        <Navbar/>
        <Routes>
          <Route index element={<Main />} />
          <Route path="/main" element={<Main />} />
          <Route path="/view" element={<ViewMechanic/>}/>
        </Routes>
        <Footer />
      </BrowserRouter>
    </AuthProvider>
    </div>
  );
}

export default App;
