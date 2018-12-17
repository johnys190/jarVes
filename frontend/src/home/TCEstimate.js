import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';

class TCEstimate extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false
        };
    }

	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }
		return (
				<div> Test</div>
			);
	}
}
export default withRouter(TCEstimate);



