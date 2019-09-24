import React, { Component } from 'react';
import './App.css';
import {
  Route,
  withRouter,
  Switch
} from 'react-router-dom';

import PrivateRoute from "../common/PrivateRoute";
import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN } from '../constants';

import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';

import { Layout, notification } from 'antd';

import Home from '../home/Home';


const { Content } = Layout;

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
    .then(response => {
      this.setState({
        currentUser: response,
        isAuthenticated: true,
        isLoading: false
      });
    }).catch(error => {
      this.setState({
        isLoading: false
      });  
    });
  }

  componentWillMount() {
    this.loadCurrentUser();
  }

  handleLogout(redirectTo="/", notificationType="success", description="You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);
    
    notification[notificationType]({
      message: 'Link Line Voyage Estimate',
      description: description,
    });
  }

  handleLogin() {
    notification.success({
      message: 'Link Line Voyage Estimate',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  render() {
    if(this.state.isLoading) {
      return <LoadingIndicator />
    }
    return (
        <Layout className="app-container">
          <AppHeader isAuthenticated={this.state.isAuthenticated} 
            currentUser={this.state.currentUser} 
            onLogout={this.handleLogout} />

          <Content className="app-content">
              <Switch>
                <Route path="/login"
                       render={(props) => <Login onLogin={this.handleLogin} {...props} />}>
                </Route>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/" 
                  component={Home}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/vessel/:id" 
                  component={Home}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/vessel/new" 
                  component={Home}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/voyageEstimate/:id" 
                  component={(props) => <Home isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/voyageEstimate/new" 
                  component={Home}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/timeCharterEstimate/:id" 
                  component={Home}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/timeCharterEstimate/new" 
                  component={Home}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/users/:id" 
                  component={Home}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/users/new" 
                  component={Home}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <PrivateRoute
                  authenticated={this.state.isAuthenticated}
                  exact path="/profile/:id" 
                  component={(props) => <Profile isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}
                  handleLogout={this.handleLogout}>
                </PrivateRoute>
                <Route component={NotFound}></Route>
              </Switch>
          </Content>
        </Layout>
    );
  }
}

export default withRouter(App);
