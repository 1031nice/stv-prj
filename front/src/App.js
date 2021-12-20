import React, {useState} from 'react';
import LoginForm from './components/LoginForm';
import axios from 'axios';
import {Cookies} from 'react-cookie'

const cookies = new Cookies();

export const setCookie = (name, value) =>{
    return cookies.set(name, value)
}

export const getCookie = (name) =>{
    return cookies.get(name);
}

export const removeCookie = (name) =>{
    return cookies.remove(name);
}


function App() {

  const [user, setUser] = useState({username: ""})

  const login = details => {
    console.log(details);

    const api = axios.create({
      baseURL: 'http://localhost:8090'
    })
    api.post('/signin', null, {
      params: {
        username: details.username,
        password: details.password
      }
    }).then(function (response) {
      console.log(response)
      console.log(response.data);
      localStorage.setItem('refresh-token',response.data['refresh-token']);
      setCookie('access-token',response.data['access-token']);
      setCookie('username',details.username);
      setUser({username: details.username})
      // document.getElementById("login_error_alert").style.display="none";
      // props.history.push({
        // pathname: '/'
      // })/
    }).catch(function (error) {
      // document.getElementById("login_error_alert").style.display="block";
      console.log(error);
    });
  }

  const logout = () => {
    console.log("Logout");
  }

  return (
    <div className="App">
      {(user.username != "") ? (
        <div className="welcome">
          <h2>Welcome, <span>{user.username}</span></h2>
          <button>Logout</button>
        </div>
      ) : (
        <LoginForm Login={login}/>
      )}
    </div>
  );
}

export default App;