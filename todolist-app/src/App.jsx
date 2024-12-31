import { useState } from 'react'
import { Login } from './pages/Login'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Todo } from './pages/Todo'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path="/todo" element={<Todo/>}/>
      </Routes>
    </BrowserRouter>
  )
}

export default App
