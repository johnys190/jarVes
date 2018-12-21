import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import { Table } from 'antd';
import { formatHumanDate } from '../util/Helpers'

class PositionList extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            columns: [{
            	title: 'Broker',
            	dataIndex: 'broker',
            	key: 'broker',
            },{
            	title: 'Name',
            	dataIndex: 'name',
            	key: 'name',
            },{
            	title: 'DWAT',
            	dataIndex: 'dwat',
            	key: 'dwat',
            },{
            	title: 'Type',
            	dataIndex: 'type',
            	key: 'type',
            },{
            	title: 'Flag',
            	dataIndex: 'flag',
            	key: 'flag',
            },{
            	title: 'Built',
            	dataIndex: 'built',
            	key: 'built',
            },{
            	title: 'Gear',
            	dataIndex: 'gear',
            	key: 'gear',
            },{
            	title: 'Grain',
            	dataIndex: 'grain',
            	key: 'grain',
            },{
            	title: 'Position',
            	dataIndex: 'position',
            	key: 'position',
            },{
            	title: 'Area',
            	dataIndex: 'area',
            	key: 'area',
            },{
            	title: 'Mng',
            	dataIndex: 'mng',
            	key: 'mng',
            },{
            	title: 'Pic',
            	dataIndex: 'pic',
            	key: 'pic',
            },{
            	title: 'Date',
            	dataIndex: 'date',
            	key: 'date',
                  render: (date) => (
                        date.toString()
                  )
            }],
            dataSource:[{
            	broker: '1',
            	name: 'test name',
            	dwat: '1234',
            	type: 'test type',
            	flag: 'test flag',
            	built: '1970',
            	gear: 'test gear',
            	grain: '1.1234',
            	position: 'test position',
            	area: 'area 51',
            	mng: 'test mng',
            	pic: '23453457',
            	date: new Date()
            }]
        };
    }

	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }
		return (
      			<Table 
      				columns={this.state.columns}
      				dataSource={this.state.dataSource}/>
			);
	}
}
export default withRouter(PositionList);



