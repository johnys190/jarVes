import React, { Component } from 'react';
import { withRouter, Link } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import { 
      Table,
      Popconfirm,
      Button,
      notification,
      message
} from 'antd';
import { formatHumanDate } from '../util/Helpers';
import { deleteVesselById, getAllVessels } from '../util/APIUtils';

class VesselList extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            columns: [{
                  title: 'Name',
                  dataIndex: 'name',
                  key: 'name',
                  render: (name, record) =>{
                        return (
                              <Link to={{ pathname: "/vessel/" + record.id, state:{vessel: this.record} }}>{name}</Link>
                              );
                  }
            },{
            	title: 'Boiler',
            	dataIndex: 'boiler',
            	key: 'boiler',
            },{
            	title: 'DWT',
            	dataIndex: 'dwt',
            	key: 'dwt',
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
            	title: 'Manager',
            	dataIndex: 'manager',
            	key: 'manager',
            },{
            	title: 'Speed',
            	dataIndex: 'speed',
            	key: 'speed',
            },{
            	title: 'Pic',
            	dataIndex: 'pic',
            	key: 'pic',
            },{
            	title: 'Date',
            	dataIndex: 'date',
            	key: 'date',
                  // render: (date) => (
                  //       date.toString()
                  // )
            },{
                  key: 'delete',
                  render: (record) => {
                        return (
                              <Popconfirm title="Are you sure delete this?"
                                    onConfirm={this.deleteRecord.bind(this, record.id)}>
                                          <Button type="danger">Delete</Button>
                              </Popconfirm>
                  )}
            }],
            dataSource:[
            // {
            //       id: '1',
            // 	broker: '1',
            // 	name: 'test name',
            // 	dwat: '1234',
            // 	type: 'test type',
            // 	flag: 'test flag',
            // 	built: '1970',
            // 	gear: 'test gear',
            // 	grain: '1.1234',
            // 	position: 'test position',
            // 	area: 'area 51',
            // 	mng: 'test mng',
            // 	pic: '23453457',
            // 	date: new Date()
            // }
            ]
        };
    }

    componentWillMount(){
        this.getAllVessels();
    }

    getAllVessels(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getAllVessels();

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                this.setState({
                    dataSource: response ? response : [],
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

      deleteRecord(id){

            let promise;

            promise = deleteVesselById(id);

            const dataSource = this.state.dataSource.filter(i => i.id !== id)
            this.setState({dataSource})

            message.success('Deleted');

      }

	render(){
            if(this.state.isLoading) {
                  return <LoadingIndicator />
            }

      	return (   <div>
                        <Button className="add-button" type="primary" href="/vessel/new">Add Vessel</Button>
      			<Table 
      				columns={this.state.columns}
      				dataSource={this.state.dataSource}/>
                        </div>
      		);
	}
}
export default withRouter(VesselList);



