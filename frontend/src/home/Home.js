import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import './Home.css'
import { Tabs } from 'antd';
import { StickyContainer, Sticky } from 'react-sticky';
import Vessel from './Vessel.js';
import VesselList from './VesselList.js';
import VoyageEstimate from './VoyageEstimate.js';
import VoyageEstimateList from './VoyageEstimateList.js';
import TCEstimate from './TCEstimate.js';
import TCEstimateList from './TCEstimateList.js';
import Testpage from './Testpage.js';
import Profile from '../user/profile/Profile.js';
import UserList from '../user/UserList.js';
import Signup from '../user/signup/Signup';

class Home extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            dk: 'vessel'
        };
    }

	render(){

		const TabPane = Tabs.TabPane;
		const renderTabBar = (props, DefaultTabBar) => (
		  <Sticky bottomOffset={80}>
		    {({ style }) => (
		      <DefaultTabBar {...props} style={{ ...style, zIndex: 1, background: '#fff' }} />
		    )}
		  </Sticky>
		);
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }

		let dk = this.state.dk;
        if (this.props.location.pathname.indexOf('/',1) > 0){
	        let l = this.props.location.pathname.indexOf('/', 1);
	        if (l === -1) l = this.props.location.pathname.length-1;
	        dk = this.props.location.pathname.substring(1,l);
	    }
        let vessels;
        if (this.props.location.pathname.startsWith('/vessel/')){
            vessels = (<Vessel id={this.props.match.params.id} />);
        }else{
        	vessels = (<VesselList /*parse props here*//>);
        }

        let voyageEstimate;
        if (this.props.location.pathname.startsWith('/voyageEstimate/')){
            voyageEstimate = (<VoyageEstimate id={this.props.match.params.id} {...this.props}/>);
        }else{
        	voyageEstimate = (<VoyageEstimateList /*parse props here*//>);
        }

        let tcEstimate;
        if (this.props.location.pathname.startsWith('/timeCharterEstimate/')){
            tcEstimate = (<TCEstimate id={this.props.match.params.id} />);
        }else{
        	tcEstimate = (<TCEstimateList /*parse props here*//>);
        }

        let adminPanel;
        if (this.props.location.pathname.startsWith('/users/new')){
        	adminPanel = (<Signup {...this.props} />);
        }else if (this.props.location.pathname.startsWith('/users/')){
            adminPanel = (<Profile id={this.props.match.params.id} />);
            dk = "users";
        }else{
        	adminPanel = (<UserList />)
        }


		return (
			<div className="container">
				<div className="content">
					<StickyContainer>
					    <Tabs activeKey={dk} renderTabBar={renderTabBar} onTabClick={() => this.props.history.push('/')} onChange={(key) => {this.setState({dk: key})}}>
					      <TabPane tab="Vessels" key="vessel">{vessels}</TabPane>
					      <TabPane tab="Voyage Estimate" key="voyageEstimate">{voyageEstimate}</TabPane>
					      <TabPane tab="Time Charter Estimate" key="timeCharterEstimate">{tcEstimate}</TabPane>
					      <TabPane tab="Admin Panel" key="users">{adminPanel}</TabPane>
					    </Tabs>
				  	</StickyContainer>
					<br/>
					<br/>
				</div>
			</div>
			);
	}
}
export default withRouter(Home);



