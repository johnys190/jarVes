import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import './Home.css'
import { Tabs } from 'antd';
import { StickyContainer, Sticky } from 'react-sticky';
import PositionList from './PositionList.js';
import VesselEntry from './VesselEntry.js';
import VoyageEstimate from './VoyageEstimate.js';
import TCEstimate from './TCEstimate.js';

class Home extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false
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
		return (
			<div className="container">
				<div className="content">
					<StickyContainer>
					    <Tabs defaultActiveKey="1" renderTabBar={renderTabBar}>
					      <TabPane tab="Position List" key="1"><PositionList /></TabPane>
					      <TabPane tab="Vessel Entry" key="2"><VesselEntry /></TabPane>
					      <TabPane tab="Voyage Estimate" key="3"><VoyageEstimate /></TabPane>
					      <TabPane tab="T/C Estimate" key="4"><TCEstimate /></TabPane>
					    </Tabs>
				  	</StickyContainer>
				</div>
			</div>
			);
	}
}
export default withRouter(Home);



